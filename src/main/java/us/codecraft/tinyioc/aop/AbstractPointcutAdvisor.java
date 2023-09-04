/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package us.codecraft.tinyioc.aop;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class for {@link org.springframework.aop.PointcutAdvisor}
 * implementations. Can be subclassed for returning a specific pointcut/advice
 * or a freely configurable pointcut/advice.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see AbstractGenericPointcutAdvisor
 * @since 1.1.2
 */
@SuppressWarnings("serial")
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor, Serializable {

    @Override
    public int hashCode() {
        return PointcutAdvisor.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PointcutAdvisor)) {
            return false;
        }
        PointcutAdvisor otherAdvisor = (PointcutAdvisor) obj;
        return (Objects.equals(getAdvice(), otherAdvisor.getAdvice()) &&
                Objects.equals(getPointcut(), otherAdvisor.getPointcut()));
    }

}
