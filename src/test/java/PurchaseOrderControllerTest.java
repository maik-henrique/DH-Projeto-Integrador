import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mvc;


    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     * @throws JsonProcessingException
     */
    @Test
    public void deveMudarStatusOrder() throws JsonProcessingException {

        PurchaseOrderInput dto = PurchaseOrderInput.builder().orderStatus(StatusEnum.ABERTO).build();

        String payload = new ObjectMapper().writeValueAsString(dto);

        try {
            mvc.perform(MockMvcRequestBuilders
                    .post("/api/v1/fresh-products/orders/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
            ).andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
