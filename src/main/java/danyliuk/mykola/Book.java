package danyliuk.mykola;

import io.spring.guides.gs_producing_web_service.BookDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mykola Danyliuk
 */
@Entity
@Table(name = "book")
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String author;

  public BookDTO toDTO(){
    BookDTO dto = new BookDTO();
    dto.setId(id);
    dto.setTitle(title);
    dto.setAuthor(author);
    return dto;
  }

}
