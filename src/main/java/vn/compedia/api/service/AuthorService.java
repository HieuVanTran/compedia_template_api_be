package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<AuthorResponse> getAll() {
        List<AuthorResponse> list = authorRepository.getAllAuthor();
        return list;
    }

    public Author getOne(Long idAuthor) {
        Author author = authorRepository.findById(idAuthor).orElse(null);
        if (author == null) {
            author = new Author();
        }
        return author;
    }

    public void validateData(AuthorCreateRequest request) throws Exception {
        if (StringUtils.isBlank(request.getNameAuthor().trim())) {
            throw new Exception("Fullname không được để trống");
        }
        if (request.getNameAuthor().trim().length() > 50) {
            throw new Exception("Độ dài FullName không quá 50 ký tự");
        }

        if (StringUtils.isBlank(request.getAddress().trim())) {
            throw new Exception("Không được để trống Address");
        }
        if (request.getAddress().trim().length() > 50) {
            throw new Exception("Độ dài Address không vượt quá 50 ký tự");
        }
        if (StringUtils.isBlank(request.getTitle().trim())) {
            throw new Exception("Không được để trống Title");
        }
        if (request.getTitle().trim().length() > 30) {
            throw new Exception("Độ dài Title không vượt quá 50 ký tự");
        }
        if (request.getNote().trim().length() > 16777215) {
            throw new Exception("Độ dài Note không vượt quá 100 ký tự");
        }
    }

    public void create(AuthorCreateRequest request) throws Exception {
        validateData(request);
        Author author = new Author();
        author.setNameAuthor(request.getNameAuthor());
        author.setAddress(request.getAddress());
        author.setTitle(request.getTitle());
        author.setNote(request.getNote());
        authorRepository.save(author);
    }

    public void update(AuthorCreateRequest request) throws Exception {
        validateData(request);
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

    public Page<AuthorResponse> search(String nameAuthor, String address, String title, String sortField, String sortOrder, Integer page, Integer size) {
        return authorRepository.search(nameAuthor, address, title, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}


