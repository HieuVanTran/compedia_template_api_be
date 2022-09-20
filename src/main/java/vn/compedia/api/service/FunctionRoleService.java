package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.FunctionRoleRepository;
import vn.compedia.api.response.FunctionRoleResponse;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
@Transactional
public class FunctionRoleService {

    @Autowired
    private FunctionRoleRepository functionRoleRepository;

    public List<FunctionRoleResponse> getByRoleId(Integer roleId) throws Exception {
        List<FunctionRoleResponse> functions = functionRoleRepository.findAll(roleId);
        if (functions.isEmpty()) {
            throw new Exception(" IS EMPTY");
        }
        return functions;
    }
}
