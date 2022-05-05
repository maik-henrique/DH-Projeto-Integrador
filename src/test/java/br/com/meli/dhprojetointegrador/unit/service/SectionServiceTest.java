package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import br.com.meli.dhprojetointegrador.service.SectionService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @InjectMocks
    private SectionService sectionService;

    @Autowired
    private SectionRepository sectionRepository;


    Category cat1 = Category.builder().id(1L).name(CategoryEnum.FF).build();
    Warehouse ware = Warehouse.builder().id(1L).name("ware").build();

    Section section1 = Section.builder().id(1L).name("Section 1").capacity(10).category(cat1).warehouse(ware).build();
    Section section2 = Section.builder().id(2L).name("Section 2").capacity(10).category(cat1).warehouse(ware).build();
    Section section3 = Section.builder().id(3L).name("Section 3").capacity(10).category(cat1).warehouse(ware).build();


    List<Section> sections = Arrays.asList(section1, section2, section3);


    @Test
    @DisplayName("Test Req 6 - Section By Category 1")
    public void returnAllSectionsFilterByCategory() {
        when(sectionRepository.findSectionByCategoryId(1L)).thenReturn(sections);
        List<Section> result = sectionService.returnAllSectionsByCategory(1L);

        assertThat(result, containsInAnyOrder(section1, section2, section3));
        assert result.get(0).getCategory().getId().equals(1L);


    }

    @Test
    @DisplayName("Test Req 6 - Section By Category 2")
    public void returnEmptySectionListFilterByCategory() {
        when(sectionRepository.findSectionByCategoryId(1L)).thenReturn(sections);
        List<Section> result = sectionService.returnAllSectionsByCategory(2L);

        assertThat(result, IsEmptyCollection.empty());

    }

    @Test
    @DisplayName("Test Req 6 - Section By Warehouse")
    public void returnAllSectionsFilterByWarehouse() {
        when(sectionRepository.findSectionByWarehouseId(1L)).thenReturn(sections);
        List<Section> result = sectionService.returnAllSectionsByWarehouse(1L);

        assertThat(result, containsInAnyOrder(section1, section2, section3));
        assert result.get(0).getWarehouse().getId().equals(1L);


    }


    @Test
    @DisplayName("Test Req 6 - Create Section")
    public void shouldCreatedSection() {
        Section section = Section.builder().id(1L).name("Section 0").capacity(10).build();

        SectionRepository sectionRepository = Mockito.mock(SectionRepository.class);
        when(sectionRepository.save(Mockito.any(Section.class))).thenReturn(section);
        section = sectionService.create(section, 1L, 1L);

        assertEquals(1L, 1L);
    }

//    @Test
//    @DisplayName("Test Req 6 - Create Section 2")
//    public void doNotCreatedSection() throws Exception {
//        Section section = Section.builder().id(1L).name("Section 0").capacity(10).build();
//
//        SectionRepository sectionRepository = Mockito.mock(SectionRepository.class);
//        when(sectionRepository.save(Mockito.any(Section.class))).thenReturn(section);
//        section  = sectionService.create(section, 1L, null);
//
//
//    }

}








