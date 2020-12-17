package danyliuk.mykola;

import io.spring.guides.gs_producing_web_service.BookDTO;
import io.spring.guides.gs_producing_web_service.BookSearchResponseDTO;
import io.spring.guides.gs_producing_web_service.CreateBookRequestDTO;
import io.spring.guides.gs_producing_web_service.EditBookRequestDTO;
import io.spring.guides.gs_producing_web_service.SearchBookRequestDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author Mykola Danyliuk
 */
@Endpoint
@AllArgsConstructor
public class BookEndpoint {

  private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

  private final BookRepository bookRepository;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchBookRequestDTO")
  @ResponsePayload
  public BookSearchResponseDTO searchBooks(@RequestPayload SearchBookRequestDTO request) {
    List<BookDTO> books = bookRepository.findAll((Specification <Book>) (root, cq, cb) -> {
      List <Predicate> predicates = new ArrayList <>();
      String searchText = request.getSearchText();
      if(searchText != null && !searchText.isEmpty()){
        for (String word: searchText.split(" ")){
          predicates.add(cb.like(root.get("searchText"), '%' + word.toUpperCase() + '%'));
        }
      }
      return cq.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
    }, Pageable.unpaged()).map(Book::toDTO).getContent();
    BookSearchResponseDTOWrapper response = new BookSearchResponseDTOWrapper();
    response.setItems(books);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateBookRequestDTO")
  @ResponsePayload
  public void createBook(@RequestPayload CreateBookRequestDTO request) {
    Book newBook = new Book(request);
    bookRepository.save(newBook);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "EditBookRequestDTO")
  @ResponsePayload
  public void editBook(@RequestPayload EditBookRequestDTO request) {
    Long id = Objects.requireNonNull(request.getId(), "Book ID is null");
    bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found book with id " + id));

    Book updatedBook = new Book(request);
    bookRepository.save(updatedBook);
  }

}
