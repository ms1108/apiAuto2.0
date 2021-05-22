package base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseIdStrCase extends BaseCase{
    public String id;
}
