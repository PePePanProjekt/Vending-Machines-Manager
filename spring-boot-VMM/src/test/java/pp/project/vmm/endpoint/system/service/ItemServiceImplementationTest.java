package pp.project.vmm.endpoint.system.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pp.project.vmm.endpoint.system.model.Item;
import pp.project.vmm.endpoint.system.repository.ItemRepository;
import pp.project.vmm.endpoint.system.service.dto.ItemDetailsDTO;
import pp.project.vmm.endpoint.system.service.dto.ItemSimpleDTO;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(SpringExtension.class)

class ItemServiceImplementationTest {
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    ItemServiceImplementation itemService;
    UUID id = UUID.randomUUID();
    @BeforeEach
    public void init()
    {
        given(itemRepository.findAll()).willReturn(prepareMockData());
        given(itemRepository.findById(id)).willReturn(prepareMockData2(id));
        given(itemRepository.existsById(id)).willReturn(TRUE);

    }

    @Test
    void getItemsTest() {

        List<ItemDetailsDTO> itemDetailsDTO = itemService.getItems();

        assertThat(itemDetailsDTO, Matchers.hasSize(3));
    }

    @Test
    void getItemsTest2() {

        List<ItemDetailsDTO> itemDetailsDTO = itemService.getItems();
        String nameItem = String.valueOf(itemDetailsDTO.get(0).getName());
        assertEquals(nameItem, "name1");
    }

    private List<Item> prepareMockData(){
        List<Item> item = new ArrayList<>();

        item.add(new Item("name1", 1));
        item.add(new Item("name2", 2));
        item.add(new Item("name3", 3));

        return item;
    }

    private Optional<Item> prepareMockData2(UUID id){
        Item item = new Item("name1", 1);
        return Optional.of(item);
    }


    @Test
    void getItemByIdTest() {
        ItemDetailsDTO itemDetailsDTO = itemService.getItemById(id);
        String nameItem = itemDetailsDTO.getName();

        assertEquals(nameItem, "name1");
    }

    @Test
    void shouldUpdateItem() {
        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "name1");
        Item item = new Item("item1", 10);
        given(itemRepository.existsById(simpleDTO.getId())).willReturn(true);
        given(itemRepository.findById(simpleDTO.getId())).willReturn(Optional.of(item));

        ResponseEntity<String> resEntity =  itemService.updateItem(simpleDTO);

        verify(itemRepository, times(1)).existsById(simpleDTO.getId());
        verify(itemRepository, times(1)).findById(simpleDTO.getId());

        assertNotNull(resEntity);
        assertEquals(HttpStatus.OK, resEntity.getStatusCode());
        assertEquals("Successfully updated item", resEntity.getBody());
    }

    @Test
    public void shouldNotUpdateItem() {

        ItemSimpleDTO simpleDTO = new ItemSimpleDTO(id, "item1");

        given(itemRepository.existsById(simpleDTO.getId())).willReturn(false);

        ResponseEntity<String> resEntity = itemService.updateItem(simpleDTO);

        verify(itemRepository, times(1)).existsById(simpleDTO.getId());
        verify(itemRepository, never()).findById(simpleDTO.getId());
        verify(itemRepository, never()).save(any());

        assertNotNull(resEntity);
        assertEquals(HttpStatus.NOT_FOUND, resEntity.getStatusCode());
        assertEquals("Item of given id does not exist", resEntity.getBody());
    }

    @Test
    void addItem() {
    }

    @Test
    void deleteItemById() {
    }
}