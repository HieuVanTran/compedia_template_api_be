package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Author;
import vn.compedia.api.repository.AuthorRepository;
import vn.compedia.api.request.AuthorCreateRequest;
import vn.compedia.api.response.book.AuthorResponse;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAll() {
        List<Author> list = authorRepository.findAll();
        return list;
    }

    public Author getOne(Long idAuthor) {
        Author author = authorRepository.findById(idAuthor).orElse(null);
        if (author == null) {
            author = new Author();
        }
        return author;
    }

    public void create(AuthorCreateRequest request) {
        Author author = new Author();
        author.setNameAuthor(request.getNameAuthor());
        author.setAddress(request.getAddress());
        author.setTitle(request.getTitle());
        author.setNote(request.getNote());
        authorRepository.save(author);
    }

    public void update(AuthorCreateRequest request) {
        Author author = authorRepository.findById(request.getId()).get();
        author.setNameAuthor(request.getNameAuthor());
        author.setAddress(request.getAddress());
        author.setTitle(request.getTitle());
        author.setNote(request.getNote());
        authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    public List<AuthorResponse> search(String nameAuthor, String title) {
        return authorRepository.search(nameAuthor, title);
    }
}


