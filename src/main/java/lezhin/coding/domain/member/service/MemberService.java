package lezhin.coding.domain.member.service;

import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.member.dto.UserWithAdultContentResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {


    List<UserWithAdultContentResDto> findUsersWithAdultContentViews(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            MinorWorkType minorWorkType
    );

    void memberDelete(Long memberId);
}
