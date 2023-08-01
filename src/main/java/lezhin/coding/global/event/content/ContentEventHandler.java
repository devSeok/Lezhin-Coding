package lezhin.coding.global.event.content;

import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.ContentLogRepository;
import lezhin.coding.global.common.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentEventHandler {
    private final ContentLogRepository contentLogRepository;

    @EventListener
    public void writeHistory(final ContentEvent.ContentHistory event) {
        this.saveWriteHistory(event.getContentId());
    }

    private void saveWriteHistory(final Long contentId) {

        ContentLogEntity build = ContentLogEntity.builder()
                .contentId(contentId)
                .memberId(SecurityUtil.getCurrentMemberId())
                .build();

        contentLogRepository.save(build);
    }
}
