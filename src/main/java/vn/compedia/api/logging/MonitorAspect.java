package vn.compedia.api.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Aspect
@Log4j2
public class MonitorAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(vn.compedia.*.repository..*)" + " || within(vn.compedia.*.controller..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void applicationControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * @throws Throwable Advice that logs all incoming request and response
     */
    @Around("controllerPointcut()")
    public Object logAllIncomingRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = getLogger(joinPoint);
        // Logs request information
        logger.info(showRequest());

        // Logs response information
        Object result = joinPoint.proceed();

        if (result instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
            logger.info(showResponse(responseEntity.getBody(), responseEntity.getStatusCode().toString()));
        }

        return result;
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws JsonProcessingException {
        Logger logger = getLogger(joinPoint);
        String statusCode = HttpStatus.BAD_REQUEST.toString();
        logger.info("statusCode = {} cause: ", statusCode, e);
    }

    /**
     * Building request informations
     *
     * @return
     */
    private String showRequest() {
        // Building request body
        StringBuilder requestBody = buildRequestBody();

        // Building response
        String template = String.format("request: {timestamp: %s - ", new Date()) +
                String.format("requestUrl: \"%s %s\" - ", request.getMethod(), request.getRequestURI()) +
                String.format("requestBody: %s}", requestBody.toString());
        return escapeHTML(template);
    }

    /**
     * Building response informations
     *
     * @param response
     * @param statusCode
     * @return String
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws JsonProcessingException
     */
    private String showResponse(Object response, String statusCode) {
        // Building request body
        StringBuilder requestBody = buildRequestBody();

        String body = "";
        if (response != null) {
            if (response instanceof ByteArrayResource) {
                body = "{}";
            } else {
                try {
                    body = mapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                    log.error("parse error", e);
                }
            }
        } else {
            body = "{}";
        }

        // Building response
        StringBuilder template = new StringBuilder();
        template.append(String.format("requestUrl: \"%s %s\" - ", request.getMethod(), request.getRequestURI()));
        template.append(String.format("requestBody: %s - ", requestBody.toString()));
        template.append(String.format("response: %s - ", body));
        template.append(String.format("status: %s}", statusCode));

        return escapeHTML(template.toString());
    }

    /**
     * Escape HTML characters
     *
     * @param template
     * @return String
     */
    private String escapeHTML(String template) {
        String data = template.replace("\"", "");
        data = data.replace("\\", "\\\\");
        data = data.replace("\b", "\\b");
        data = data.replace("\f", "\\f");
        data = data.replace("\r", "\\r");
        data = data.replace("\n", "\\n");
        data = data.replace("\t", "");

        return data;
    }

    private String getPathVariable() {
        StringBuilder pathVariables = new StringBuilder();
        Map<String, String> pathVariablesMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Iterator<?> it = pathVariablesMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if (!Strings.isBlank(entry.getValue())) {
                continue;
            }

            if (Strings.isNotEmpty(pathVariables)) {
                pathVariables.append(", ");
            }
            pathVariables.append("{" + entry.getKey() + ": " + entry.getValue() + "}");
        }
        pathVariables.insert(0, "[");
        pathVariables.append("]");

        return !Strings.isBlank(pathVariables.toString()) ? pathVariables.toString() : "[]";
    }

    private StringBuilder buildRequestBody() {
        StringBuilder requestBody = new StringBuilder();

        String body = "[]";

        // Building path variables
        String pathVariable = getPathVariable();

        requestBody.append("{body: ").append(body).append(", ");
        requestBody.append("pathVariables: ").append(pathVariable).append("}");

        return requestBody;
    }


    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getTarget().getClass().getName());
    }
}
