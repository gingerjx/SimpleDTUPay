package dtu.example;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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