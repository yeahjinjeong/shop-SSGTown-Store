package com.sh.admin.item.model.dao;

import com.sh.admin.item.model.dto.Artist;
import com.sh.admin.item.model.dto.Category;
import com.sh.admin.item.model.dto.ItemDto;
import com.sh.admin.item.model.dto.ItemDto;
import com.sh.admin.item.model.dto.SaleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemMapperTest {

    @Autowired
    private ItemMapper itemMapper;


    @Test
    @DisplayName("전체조회")
    void findAll() {
        // given // 조회라서 줘야되는게 없음
        // when
        List<ItemDto> items = itemMapper.findAll();
        System.out.println(items);

        //then
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .allMatch((item) -> item != null); // 모든 요소가 다 true

    }
  
    @Test
    void findAllMatch() {
        // given
        Category categoryName = Category.valueOf("CD");
        Artist artistName = Artist.valueOf("샤이니");

        // when
        List<ItemDto> items = itemMapper.findAllMatch(categoryName,artistName,13000,null);
        System.out.println(items);
        // then
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .allMatch((item) -> item != null); // 모든 요소가 다 true
    }

//    나경작업시작
    @Test
    @DisplayName("상품아이디로 한건조회")
    void findById() {
        //given
        long itemId = 1;
        //when
        ItemDto itemDto = itemMapper.findById(itemId);

        //then
        assertThat(itemDto)
                .isNotNull()
                .satisfies(
                        (_itemDto) -> assertThat(_itemDto.getItemId()).isEqualTo(itemId),
                        (_itemDto) -> assertThat(_itemDto.getItemName()).isNotNull(),
                        (_itemDto) -> assertThat(_itemDto.getImgUrl()).isNotNull(),
                        (_itemDto) -> assertThat(_itemDto.getSeller()).isEqualTo("SSG"),
                        (_itemDto) -> assertThat(_itemDto.getQuantity()).isPositive(),
                        (_itemDto) -> assertThat(_itemDto.getSalePrice()).isPositive(),
                        (_itemDto) -> assertThat(_itemDto.getSaleStatus()).satisfiesAnyOf(
                                (saleStatus) -> assertThat(saleStatus).isEqualTo(saleStatus.판매중),
                                (saleStatus) -> assertThat(saleStatus).isEqualTo(saleStatus.품절),
                                (saleStatus) -> assertThat(saleStatus).isEqualTo(saleStatus.판매중지)
                        ),
                        (_itemDto) -> assertThat(_itemDto.getCategoryName()).satisfiesAnyOf(
                                (categoryName) -> assertThat(categoryName).isEqualTo(categoryName.CD),
                                (categoryName) -> assertThat(categoryName).isEqualTo(categoryName.응원봉),
                                (categoryName) -> assertThat(categoryName).isEqualTo(categoryName.의류),
                                (categoryName) -> assertThat(categoryName).isEqualTo(categoryName.잡화)
                        ),
                        (_itemDto) -> assertThat(_itemDto.getArtistName()).satisfiesAnyOf(
                                (artist) -> assertThat(artist).isEqualTo(artist.에스파),
                                (artist) -> assertThat(artist).isEqualTo(artist.샤이니)
                        )



                );

    }
    @Test
    @DisplayName("품절상태인걸 판매중지로 바꿔지는가?")
    void updateSaleStatus() {
        // given
        Long itemId = 30L; // 품절상태


        // when
        int updatedRows = itemMapper.updateSaleStatus(itemId);

        // then
        assertThat(updatedRows).isEqualTo(1); // 업데이트된 행의 수가 0보다 큰지 확인합니다.



    }
}