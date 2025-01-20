import com.pvin.curso.springboot.di.factura.models.Item;
import com.pvin.curso.springboot.di.factura.models.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:data.properties")
public class AppConfig {

    @Bean
    List<Item> itemsInvoice() {
        Product p1 = new Product("Camara Sonny", 800);
        Product p2 = new Product("Bicicleta Bianchi 26", 1200);

        return Arrays.asList(new Item(p1, 2), new Item(p2, 4));
    }
}
