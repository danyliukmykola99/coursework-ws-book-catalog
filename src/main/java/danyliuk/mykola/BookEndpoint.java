package danyliuk.mykola;

import io.spring.guides.gs_producing_web_service.BookRequestDTO;
import io.spring.guides.gs_producing_web_service.BookResponseDTO;

import lombok.AllArgsConstructor;
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

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BookRequestDTO")
  @ResponsePayload
  public BookResponseDTO getBookByID(@RequestPayload BookRequestDTO request) {
    Long id = request.getId();
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book with id " + id));
    BookResponseDTO response = new BookResponseDTO();
    response.setBook(book.toDTO());
    return response;
  }

}
