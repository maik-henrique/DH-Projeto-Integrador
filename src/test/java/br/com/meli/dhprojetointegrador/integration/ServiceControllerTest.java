package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test Int Req 6 - return sections by Warehouse Id")
    public void shouldReturnSectionsByIdWarehouse() throws Exception {
        Warehouse warehouse = setupWarehouse(1L, "ware");
        Category category = setupCategory(1L, CategoryEnum.FF);
        Section section = setupSection(1L, "Section 1", 10f, warehouse, category);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/sections").param("warehouseId", "1L"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        Section section1 = objectMapper.readValue(responsePayload, Section.class);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());


    }

    private Warehouse setupWarehouse(Long id, String name) {
        Warehouse warehouse = Warehouse.builder().id(id).name(name).build();
        warehouseRepository.save(warehouse);
        return warehouse;
    }

    private Category setupCategory(Long id, CategoryEnum name) {
        Category category = Category.builder().id(id).name(name).build();
        categoryRepository.save(category);
        return category;
    }

    private Section setupSection(Long id, String name, Float capacity, Warehouse warehouse, Category category) throws Exception {
        Section section = Section.builder().id(id).name(name).capacity(capacity).warehouse(warehouse).category(category).build();
        sectionRepository.save(section);
        return section;
    }
}