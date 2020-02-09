package za.co.entelect.superman.superman.api;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.superman.superman.dto.AddressDTO;
import za.co.entelect.superman.superman.dto.CustomerDTO;
import za.co.entelect.superman.superman.dto.OrderDTO;
import za.co.entelect.superman.superman.Service.Customer.CustomerService;
import za.co.entelect.superman.superman.domain.Address;
import za.co.entelect.superman.superman.exceptions.AddressNotFoundException;
import za.co.entelect.superman.superman.exceptions.CustomerNotFoundException;
import za.co.entelect.superman.superman.exceptions.UnableToCreateAddressException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    private final Logger LOGGER   = LoggerFactory.getLogger(BrowsingController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getOneCustomer(@PathVariable Integer customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = null;

        if(customerId != null)
        {
            try
            {
                customerDTO = modelMapper.map(customerService.findOne(customerId),CustomerDTO.class);
                return ResponseEntity.ok(customerDTO);
            }
            catch (DataIntegrityViolationException e)
            {
                LOGGER.error("Could not find customer with customerId {}", customerId.toString());
                throw new CustomerNotFoundException("Addresses not found", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("customerId is null");
        }


    }

    @GetMapping("/Addresses/{customerId}")
    public ResponseEntity<List<AddressDTO>> getCustomerAddresses(@PathVariable Integer customerId) throws AddressNotFoundException {

        if(customerId != null)
        {
            try
            {
                return ResponseEntity.ok(customerService.findAllAddresses(customerId));
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error("Could not find addresses with customerId {}", customerId.toString());
                throw new AddressNotFoundException("Addresses not found", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("customerId is null");
        }

    }

    @GetMapping("/Orders/{customerId}/{pageNumber}")
    public ResponseEntity<Page<OrderDTO>> getCustomerOrders(@PathVariable Integer customerId, @PathVariable Integer pageNumber) throws CustomerNotFoundException {

        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(Sort.Direction.DESC,"orderDate"));
        Page<OrderDTO > orders = null;
        
        if(customerId != null)
        {
            try
            {

                orders = customerService.findAllOrders(customerId,pageable).map(OrderDTO::new);
                LOGGER.info("{} retrieved ", customerId.toString());
                return ResponseEntity.ok(orders);
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error("Could not find customer with id {}", customerId.toString());
                throw new CustomerNotFoundException("Customer not found", e.getCause());
            }

        }
        else
        {
            throw new NullPointerException("customerId is null");
        }
    }

    @GetMapping("/Order/{customerId}")
    public ResponseEntity<OrderDTO> getCustomerOrder(@PathVariable Integer customerId, @RequestParam Integer orderId) throws CustomerNotFoundException {

        OrderDTO orderDTO = null;
        if(customerId != null)
        {
            try
            {
                orderDTO = modelMapper.map(customerService.findOneOrder(customerId,orderId),OrderDTO.class);
                LOGGER.info("{} retrieved ", customerId.toString());
                return ResponseEntity.ok(orderDTO);
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error("Could not find customer with id {}", customerId.toString());
                throw new CustomerNotFoundException("Customer not found", e.getCause());
            }

        }
        else
        {
            throw new NullPointerException("customerId is null");
        }

    }

    @RequestMapping(value = "/AddAddress", method = RequestMethod.POST)
    public boolean addCustomerAddress(@RequestBody Address address) throws UnableToCreateAddressException
    {
        if(address != null)
        {
            try
            {
                customerService.addAddress(address);
                LOGGER.info("{} added ", address.toString());
                return true;
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error("Could not add {} address", address.toString());
                throw new UnableToCreateAddressException("Item already exists", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("Address is null");
        }


    }

}
