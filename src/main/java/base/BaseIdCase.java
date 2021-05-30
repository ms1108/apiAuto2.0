package base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BaseIdCase extends BaseCase{
    public Integer id;
}
