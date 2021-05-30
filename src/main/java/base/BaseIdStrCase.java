package base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BaseIdStrCase extends BaseCase{
    public String id;
}
