package gitp.upbitapi.repository;

import gitp.upbitapi.domain.Candle;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CandleRepository {

    private final EntityManager em;

    public long save(Candle candle) {
        if (candle.getId() != null) {
            return em.merge(candle)
                    .getId();
        } else {
            em.persist(candle);
            return candle.getId();
        }
    }

    public List<Candle> findAll() {
        return em.createQuery("SELECT c FROM Candle c", Candle.class)
                .getResultList();
    }
}
