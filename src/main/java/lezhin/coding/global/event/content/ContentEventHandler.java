package lezhin.coding.global.event.content;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.exception.error.exception.ContentNotException;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lezhin.coding.global.exception.error.exception.UserNotException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentEventHandler {
    private final ContentLogRepository contentLogRepository;
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;

    @EventListener
    public void writeHistory(final ContentEvent.ContentHistory event) {
        this.saveWriteHistory(event.getContentId());
    }

    private void saveWriteHistory(final Long contentId) {

        MemberEntity findMember = findMemberEntity();
        ContentEntity findContent = findContentEntity(contentId);

        ContentLogEntity contentLogEntity = ContentLogEntity.create(findMember, findContent);

        contentLogRepository.save(contentLogEntity);
    }

    private MemberEntity findMemberEntity() {
        System.out.println("================");
        System.out.println(SecurityUtil.getCurrentMemberEmail().getValue());
        return memberRepository.findByUserEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new UserNotException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private ContentEntity findContentEntity(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotException(ErrorCode.CONTENT_NOT_FOUND.getMessage()));
    }


}
