package za.co.entelect.superman.superman.Service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.entelect.superman.superman.domain.Cart;
import za.co.entelect.superman.superman.domain.CartCompoundKey;
import za.co.entelect.superman.superman.dto.CartDTO;
import za.co.entelect.superman.superman.dto.ResponseCartDTO;
import za.co.entelect.superman.superman.persistance.CartSpringRepository;
import za.co.entelect.superman.superman.persistance.StockSpringRepository;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartManagementService {

    private CartSpringRepository cartSpringRepository;
    private StockSpringRepository stockSpringRepository;

    @Autowired
    public CartManagementService(CartSpringRepository cartSpringRepository,StockSpringRepository stockSpringRepository) {
        this.cartSpringRepository = cartSpringRepository;
        this.stockSpringRepository = stockSpringRepository;
    }

    public List<CartDTO> getCustomerCart(Integer customerID){
        List<Cart> cart = cartSpringRepository.findByCustomerId(customerID);
        List<CartDTO> cartDTO = new ArrayList<>();
        cart.forEach(item -> {
            cartDTO.add(new CartDTO(item.getCartCompoundKey().getCustomerId(),item.getCartCompoundKey().getStockReference().getStockReferenceId(),
                    item.getCartCompoundKey().getStockReference().getIssue().getTitle(),
                    item.getQuantity(),
                    item.getCartCompoundKey().getStockReference().getIssue().getSeriesNumber()));
        });
        return cartDTO;
    }

    public void DeleteCart(int customerID){
        for (Cart x : cartSpringRepository.findByCustomerId(customerID)
             ) {
            if(x.getCartCompoundKey().getCustomerId()== customerID){
                cartSpringRepository.delete(x);
            }
        }
    }

    public void RemoveCartItem(int customerId, int stockReferenceID){
        for (Cart x: cartSpringRepository.findByCustomerId(customerId)
             ) {
            if(x.getCartCompoundKey().getCustomerId()== customerId &&
                    x.getCartCompoundKey().getStockReference().getStockReferenceId()==stockReferenceID
                    ){
                cartSpringRepository.delete(x);
            }
        }
    }

    public Cart getSingleCartItem(Integer customerId, Integer stockReference) {
        for (Cart x: cartSpringRepository.findByCustomerId(customerId)
                ) {
            if(x.getCartCompoundKey().getCustomerId()== customerId &&
                    x.getCartCompoundKey().getStockReference().getStockReferenceId()==stockReference
                    ){
                return x;
            }
        }
        return null;
    }

    public List<CartDTO> addCartItem(ResponseCartDTO cartDTO){
        Cart cartItem = new Cart();
        CartCompoundKey cartCompoundKey = new CartCompoundKey();
        cartCompoundKey.setStockReference(stockSpringRepository.findByStockReferenceId(cartDTO.getStockReferenceID()));
        cartCompoundKey.setCustomerId(cartDTO.getCustomerID());
        cartItem.setCartCompoundKey(cartCompoundKey);
        cartItem.setQuantity(cartDTO.getQuantity().byteValue());
        cartItem.setModificationDate(Date.from(Instant.now()));
        cartSpringRepository.save(cartItem);
        return this.getCustomerCart(cartDTO.getCustomerID());
    }

    public List<CartDTO> removeCartItem(ResponseCartDTO cartDTO){
        Cart cartItem = new Cart();
        CartCompoundKey cartCompoundKey = new CartCompoundKey();
        cartCompoundKey.setStockReference(stockSpringRepository.findByStockReferenceId(cartDTO.getStockReferenceID()));
        cartCompoundKey.setCustomerId(cartDTO.getCustomerID());
        cartItem.setCartCompoundKey(cartCompoundKey);
        cartItem.setQuantity(cartDTO.getQuantity().byteValue());
        cartItem.setModificationDate(Date.from(Instant.now()));
        cartSpringRepository.delete(cartItem);
        return this.getCustomerCart(cartDTO.getCustomerID());
    }

    public List<Cart> updateQuantity(Cart cartItem){
        Cart newCart = getSingleCartItem(cartItem.getCartCompoundKey().getCustomerId(),
                cartItem.getCartCompoundKey().getStockReference().getStockReferenceId());
        newCart.setQuantity(cartItem.getQuantity());
        cartSpringRepository.save(newCart);
        return cartSpringRepository.findByCustomerId(cartItem.getCartCompoundKey().getCustomerId());
    }


}


