package simple.dtu.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String customerId;
    private String merchantId;
    private BigDecimal amount;
}