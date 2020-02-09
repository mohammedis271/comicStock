package za.co.entelect.superman.superman.Service.Customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import za.co.entelect.superman.superman.domain.Address;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;
import za.co.entelect.superman.superman.persistance.AddressSpringRepository;
import za.co.entelect.superman.superman.persistance.CustomerSpringRepository;
import za.co.entelect.superman.superman.persistance.OrdersSpringRepository;
import za.co.entelect.superman.superman.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerService {
    private CustomerSpringRepository customerSpringRepository;
    private AddressSpringRepository addressSpringRepository;
    private OrdersSpringRepository ordersSpringRepository;

    @Autowired
    public CustomerService(CustomerSpringRepository customerSpringRepository, AddressSpringRepository addressSpringRepository, OrdersSpringRepository ordersSpringRepository) {
        this.customerSpringRepository = customerSpringRepository;
        this.addressSpringRepository = addressSpringRepository;
        this.ordersSpringRepository = ordersSpringRepository;

    }

    public Customer findOne(Integer customerId) {
        return customerSpringRepository.getOne(customerId);
    }

    public List<AddressDTO> findAllAddresses(Integer customerId) {
        List<Address> addresses = addressSpringRepository.findAllByCustomerId(customerId);
        List<AddressDTO> addressesDTO = new ArrayList<>();
        addresses.forEach(address -> {
            addressesDTO.add(new AddressDTO(address.getName(),address.getLineOne(),
                    address.getLineTwo(),address.getSuburb(),address.getCity(),address.getPostal()));
        });
        return addressesDTO;
    }

    public Page<Orders> findAllOrders(Integer customerId, Pageable pageable) {
        return ordersSpringRepository.findAllByCustomerId(customerId, pageable);
    }

    public Orders findOneOrder(Integer customerId, Integer orderId) {
        return ordersSpringRepository.findByCustomerIdAndOrderId(customerId, orderId);

    }

    public Address addAddress(Address address) {
        return addressSpringRepository.save(address);
    }

}
