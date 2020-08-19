package jack.changgou.service;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Album;

import java.util.List;

public interface AlbumService {
    /**
     * 查询所有相册
     * @return
     */
    List<Album> getAllAlbum();
    /**
     * 根据Id查询相册
     * @return
     */
    Album getAlbumById(Integer id);
    /**
     * 添加相册
     * @return
     */
    void addAlbum(Album album);
    /**
     * 修改品牌
     * @return
     */
    void modefyAlbum(Album album);
    /**
     * 根据Id删除相册
     * @return
     */
    void deleteAlbumById(Integer id);
    /**
     * 根据条件查询相册
     * @return
     */
    List<Album> searchAlbum(Album album);
    /**
     * 分页查询所有的相册
     * @return
     */
    PageInfo<Album> getAllAlbumPaged(Integer page, Integer size);
    /**
     * 根据条件查询所有的品牌
     * @param album
     * @param page
     * @param size
     * @return
     */
    PageInfo<Album> searchAlbumPaged(Album album, Integer page, Integer size);
}
