package lezhin.coding.global.event.content;

import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.exception.error.exception.UserNotException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentEventHandler {
    private final ContentLogRepository contentLogRepository;
    private final MemberRepository memberRepository;

    @EventListener
    public void writeHistory(final ContentEvent.ContentHistory event) {
        this.saveWriteHistory(event.getContentId());
    }

    private void saveWriteHistory(final Long contentId) {

        MemberEntity findMember = memberRepository.findByUserEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new UserNotException("유저 정보가 없습니다."));

        ContentLogEntity contentLogEntity = ContentLogEntity.create(contentId, findMember.getId());

        contentLogRepository.save(contentLogEntity);
    }
}
