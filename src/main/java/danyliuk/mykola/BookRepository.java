package danyliuk.mykola;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mykola Danyliuk
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
