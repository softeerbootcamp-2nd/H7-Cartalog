package softeer.wantcar.cartalog.chosen.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.chosen.ChosenConfig;

@Slf4j
@RestController
@RequestMapping("/chosen")
public class ChosenController {

    @PostMapping("")
    public boolean setUpChosenMode(boolean activate) {
        ChosenConfig.isActivate = activate;
        log.info("선택률 계산이 {}되었습니다.", activate);
        return ChosenConfig.isActivate;
    }
}
