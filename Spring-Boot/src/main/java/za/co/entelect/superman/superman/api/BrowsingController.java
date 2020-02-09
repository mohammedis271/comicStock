package za.co.entelect.superman.superman.api;

import com.sun.org.apache.bcel.internal.generic.DADD;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
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

import za.co.entelect.superman.superman.dto.IssueDetailsDTO;
import za.co.entelect.superman.superman.dto.StockDTO;
import za.co.entelect.superman.superman.Service.browse.BrowsingService;
import za.co.entelect.superman.superman.exceptions.StockItemNotFoundException;
import za.co.entelect.superman.superman.persistance.StockSpringRepository;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Browse")
public class BrowsingController{

    private final BrowsingService browsingService;

    private final Logger LOGGER   = LoggerFactory.getLogger(BrowsingController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BrowsingController(BrowsingService browsingService) {
        this.browsingService = browsingService;
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<Page<StockDTO>> stockList(@PathVariable Integer pageNumber, @RequestParam (defaultValue = "issue") String sort) throws StockItemNotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, 20, Sort.by(Sort.Direction.ASC, sort));
        Page<StockDTO> stock;
        if(pageNumber != null) {


            try {
                stock = browsingService.getAllStock(pageable).map(StockDTO::new);
                LOGGER.info("{} retrieved ", stock.toString());
            } catch (DataIntegrityViolationException e) {
                LOGGER.error(e.getMessage());
                throw new StockItemNotFoundException("Items not found", e.getCause());
            }
            return ResponseEntity.ok(stock);
        }
        else
        {
            throw new NullPointerException("pageNumber is null");
        }
    }


    @GetMapping("/SearchTitle/{pageNumber}")
    public ResponseEntity<Page<StockDTO>> stockTitle(@RequestParam(defaultValue = "A") String title, @PathVariable Integer pageNumber, @RequestParam (defaultValue = "issue") String sort) throws StockItemNotFoundException{

        Pageable pageable = PageRequest.of(pageNumber, 20,Sort.by(Sort.Direction.ASC, sort));
        Page<StockDTO> stock;
        if(title != null) {


            try {
                stock = browsingService.GetStockbyTitle(title, pageable).map(StockDTO::new);
                LOGGER.info("Retrieving products with title {} ", title.toString());
            } catch (DataIntegrityViolationException e) {
                LOGGER.error(e.getMessage());
                throw new StockItemNotFoundException("Items with" + title + " not found", e.getCause());
            }


            return ResponseEntity.ok(stock);
        }
        else
        {
            throw new NullPointerException("title is null");
        }
    }

    @GetMapping("/SearchPublisher/{pageNumber}")
    public ResponseEntity<Page<StockDTO>> stockPublisher(@RequestParam(required = false) String publisher, @PathVariable Integer pageNumber, @RequestParam(defaultValue = "") String sort) throws StockItemNotFoundException {

        Pageable pageable = PageRequest.of(pageNumber, 20,Sort.by(Sort.Direction.ASC, sort));
        Page<StockDTO> stock;

        if(publisher != null) {
            try {
                stock = browsingService.GetStockbyPublisher(publisher, pageable).map(StockDTO::new);
                LOGGER.info("Retrieving products with publisher {} ", publisher.toString());
            } catch (DataIntegrityViolationException e) {
                LOGGER.error(e.getMessage());
                throw new StockItemNotFoundException("Items with" + publisher + " not found", e.getCause());
            }

            return ResponseEntity.ok(stock);
        }
        else
        {
            throw new NullPointerException("publisher is null");
        }
    }

    @GetMapping("/ViewItem/{ID}")
    public ResponseEntity<IssueDetailsDTO> findByStockReferenceId(@PathVariable Integer ID) throws StockItemNotFoundException{

        IssueDetailsDTO issueDetailsDTO;
        if(ID != null)
        {
            try
            {

                issueDetailsDTO = modelMapper.map(browsingService.getfindByStockReferenceId(ID),IssueDetailsDTO.class);
                LOGGER.info("Retrieving products with id {} ", ID);
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new StockItemNotFoundException("Items with" + ID + " not found", e.getCause());
            }

            return ResponseEntity.ok(issueDetailsDTO);
        }
        else
        {
            throw new NullPointerException("ID is null");
        }

    }



}
