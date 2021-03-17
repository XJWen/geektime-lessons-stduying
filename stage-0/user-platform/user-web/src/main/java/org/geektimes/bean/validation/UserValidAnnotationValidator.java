package org.geektimes.bean.validation;

import org.geektimes.projects.user.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {

    private int idRange;
    /**
     * 初始化方法
     * */
    @Override
    public void initialize(UserValid annotation) {
        this.idRange = annotation.idRange();
    }

    /**
     * 校验方法
     * @param context 校验器上下文
     * */
    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {

        // 获取模板信息
        context.getDefaultConstraintMessageTemplate();

        return false;
    }
}
