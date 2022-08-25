package vn.compedia.api.repository.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import vn.compedia.api.repository.CallCardDetailsRepositoryCustom;
import vn.compedia.api.response.CallCardDetailsResponse;
import vn.compedia.api.util.ValueUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CallCardDetailsRepositoryImpl implements CallCardDetailsRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<CallCardDetailsResponse> findAllCallCardDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select" +
                "       cd.call_card_details_id," +
                "       cd.amount," +
                "       b.book_id," +
                "       b.book_name " +
                "" +
                "from call_card_details cd " +
                "inner join book b on cd.book_id = b.book_id " +
                "where 1 = 1");
        sb.append(" ORDER BY cd.call_card_details_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<CallCardDetailsResponse> CallCardDetailsResponse = new ArrayList<>();
        for (Object[] obj : result) {
            CallCardDetailsResponse dto = new CallCardDetailsResponse();
            dto.setCallCardDetailsId(ValueUtil.getLongByObject(obj[0]));
            dto.setAmount(ValueUtil.getIntegerByObject(obj[1]));
            dto.setBookId(ValueUtil.getLongByObject(obj[2]));
            dto.setBookName(ValueUtil.getStringByObject(obj[3]));

            CallCardDetailsResponse.add(dto);
        }
        return CallCardDetailsResponse;
    }
    @Override
    public Optional<CallCardDetailsResponse> findByIdCallCardDetails(Long callCardDetailsId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cd.call_card_details_id," +
                "       cd.amount," +
                "       b.book_id," +
                "       b.book_name " +
                "" +
                "from call_card_details cd " +
                "         inner join book b on cd.book_id = b.book_id " +
                "" +
                "where cd.call_card_details_id = :callCardDetailsId");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("callCardDetailsId",callCardDetailsId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                CallCardDetailsResponse dto = new CallCardDetailsResponse();
                dto.setCallCardDetailsId(ValueUtil.getLongByObject(obj[0]));
                dto.setAmount(ValueUtil.getIntegerByObject(obj[1]));
                dto.setBookId(ValueUtil.getLongByObject(obj[2]));
                dto.setBookName(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }

}
