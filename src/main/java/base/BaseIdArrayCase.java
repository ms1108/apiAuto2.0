package base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseIdArrayCase extends BaseCase{
    public Integer[] id;
}
