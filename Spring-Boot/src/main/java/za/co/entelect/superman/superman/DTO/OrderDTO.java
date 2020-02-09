package za.co.entelect.superman.superman.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.entelect.superman.superman.domain.Orders;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private Integer OrderId;

    private String CustomerName;

    private Date OrderDate;

    private String Status;

    private String TrackingNumber;

    private String Addressline1;

    public OrderDTO(Orders orders) {

        OrderId = orders.getOrderId();
        CustomerName = orders.getCustomer().getName()+" "+orders.getCustomer().getSurname();
        OrderDate = orders.getOrderDate();
        Status = orders.getStatus();
        TrackingNumber = orders.getTrackingNum();
        Addressline1 = orders.getAddressLine1();
    }
}
