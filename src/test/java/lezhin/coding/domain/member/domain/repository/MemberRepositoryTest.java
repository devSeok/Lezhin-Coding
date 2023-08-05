package lezhin.coding.domain.member.domain.repository;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }


    @DisplayName("회원가입된 사용자를 이메일로 조회한다.")
    @Test
    void findByUserEmail() {
        // given
        UserEmail email = getUserEmail("test1@naver.com");
        String name = "진석";
        MemberEntity member1 = createMember(email, name);
        memberRepository.save(member1);

        //when
        Optional<MemberEntity> findByUserEmail = memberRepository.findByUserEmail(email);

        //then
        assertThat(findByUserEmail).isPresent();
        MemberEntity memberEntity = findByUserEmail.get();
        assertThat(memberEntity.getUserEmail().getValue()).isEqualTo(email.getValue());
    }

    @DisplayName("이메일 존재 여부 체크시, 존재하지 않는다.")
    @Test
    void findByUserEmailNot() {
        // given
        UserEmail email = getUserEmail("test1@naver.com");
        String name = "진석";
        MemberEntity member1 = createMember(email, name);
        memberRepository.save(member1);

        //when
        Optional<MemberEntity> findByUserEmail = memberRepository.findByUserEmail(getUserEmail("tes33t1@naver.com"));

        assertThat(findByUserEmail).isEmpty();
    }



    @DisplayName("회원가입시 중복된 이메일이 있는지 체크 한다.")
    @Test
    void existsUserEmail() {
        // given
        MemberEntity member1 = createMember(getUserEmail("test1@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test2@naver.com"), "소연이 사랑해");
        memberRepository.saveAll(List.of(member1, member2));

        //when
        boolean existsByUserEmailBoolean = memberRepository.existsByUserEmail(getUserEmail("test1@naver.com"));
        //then
        assertThat(existsByUserEmailBoolean).isTrue();
    }

    @DisplayName("회원 가입시 중복된 이메일이 없는걸 체크 한다.")
    @Test
    void existsNotUserEmail() {
        // given
        MemberEntity member1 = createMember(getUserEmail("test1@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test2@naver.com"), "진석");
        memberRepository.saveAll(List.of(member1, member2));

        //when
        boolean existsByUserEmailBoolean = memberRepository.existsByUserEmail(getUserEmail("test3@naver.com"));
        //then
        assertThat(existsByUserEmailBoolean).isFalse();
    }

    private MemberEntity createMember(UserEmail email, String name) {


        UserName userName = UserName.builder()
                .value(name)
                .build();

        return MemberEntity.builder()
                .userEmail(email)
                .userName(userName)
                .password("test")
                .build();
    }

    private UserEmail getUserEmail(String email) {
        UserEmail userEmail = UserEmail.builder()
                .value(email)
                .build();
        return userEmail;
    }

}