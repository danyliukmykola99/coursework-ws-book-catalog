package danyliuk.mykola;

import io.spring.guides.gs_producing_web_service.BookDTO;
import java.util.List;

/**
 * @author Mykola Danyliuk
 */
public class BookSearchResponseDTOWrapper extends io.spring.guides.gs_producing_web_service.BookSearchResponseDTO{

  public void setItems(List<BookDTO> items){
    this.items = items;
  }

}
