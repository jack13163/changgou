package jack.changgou.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.InsertListenPoint;

import java.util.List;

/**
 * 事件监听
 */
@CanalEventListener
public class DatabaseListener {
    @InsertListenPoint
    public void insertListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        for (CanalEntry.Column column : afterColumnsList) {
            System.out.println(column.getName() + ":" + column.getValue());
        }
    }
}
