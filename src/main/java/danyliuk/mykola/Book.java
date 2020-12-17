package danyliuk.mykola;

import io.spring.guides.gs_producing_web_service.BookDTO;
import io.spring.guides.gs_producing_web_service.CreateBookRequestDTO;
import io.spring.guides.gs_producing_web_service.EditBookRequestDTO;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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

  @NotBlank
  private String title;

  @NotBlank
  private String author;

  @NotBlank
  @Column(name = "search_text")
  private String searchText;

  public BookDTO toDTO(){
    BookDTO dto = new BookDTO();
    dto.setId(id);
    dto.setTitle(title);
    dto.setAuthor(author);
    return dto;
  }

  public Book(CreateBookRequestDTO request){
    this.title = Objects.requireNonNull(request.getTitle(), "Назва має бути заповненою");
    this.author = Objects.requireNonNull(request.getAuthor(), "Автор має бути заповненим");
    this.searchText = (title + " " + author).toUpperCase();
  }

  public Book(EditBookRequestDTO request){
    this.id = request.getId();
    this.title = Objects.requireNonNull(request.getTitle(), "Назва має бути заповненою");
    this.author = Objects.requireNonNull(request.getAuthor(), "Автор має бути заповненим");
    this.searchText = (title + " " + author).toUpperCase();
  }

}
