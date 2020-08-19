package jack.changgou.controller;

import com.github.pagehelper.PageInfo;
import jack.changgou.goods.pojo.Album;
import jack.changgou.service.AlbumService;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public Result<List<Album>> allAlbum() {
        List<Album> albums = albumService.getAllAlbum();
        Result<List<Album>> result = new Result<>(true, StatusCode.OK, "查询相册数据成功", albums);
        return result;
    }

    @GetMapping("/{id}")
    public Result<Album> getAlbum(@PathVariable("id") Integer id) {
        Album album = albumService.getAlbumById(id);
        Result<Album> result = null;
        if (album != null) {
            result = new Result<>(true, StatusCode.OK, "查询指定相册数据成功", album);
        } else {
            result = new Result<>(true, StatusCode.OK, "没有查询到当前相册数据", null);
        }
        return result;
    }

    @PostMapping
    public Result<String> getAlbum(@RequestBody Album album) {
        albumService.addAlbum(album);
        Result<String> result = new Result<>(true, StatusCode.OK, "插入相册信息成功", null);
        return result;
    }

    /**
     * 注解@RequestBody将传入的json字符串解析为java对象
     *
     * @param id
     * @param album
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> modefyAlbum(@PathVariable("id") Long id, @RequestBody Album album) {
        album.setId(id);
        albumService.modefyAlbum(album);
        Result<String> result = new Result<>(true, StatusCode.OK, "修改相册信息成功", null);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result<String> modefyAlbum(@PathVariable("id") Integer id) {
        albumService.deleteAlbumById(id);
        Result<String> result = new Result<>(true, StatusCode.OK, "删除相册成功", null);
        return result;
    }

    /**
     * 根据相册名和首字母搜索
     *
     * @param album
     * @return
     */
    @PostMapping("/search")
    public Result<List<Album>> searchAlbum(@RequestBody Album album) {
        List<Album> brands = albumService.searchAlbum(album);
        Result<List<Album>> result = new Result<>(true, StatusCode.OK, "根据条件查询相册成功", brands);
        return result;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @GetMapping("/{page}/{size}")
    public Result<PageInfo<Album>> searchAlbumPaged(@PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Album> albums = albumService.getAllAlbumPaged(page, size);
        Result<PageInfo<Album>> result = new Result<>(true, StatusCode.OK, "根据条件查询相册成功", albums);
        return result;
    }

    /**
     * 条件分页查询
     *
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Album>> searchAlbumPaged(@RequestBody Album album,
                                                    @PathVariable("page") Integer page,
                                                    @PathVariable("size") Integer size) {
        PageInfo<Album> brands = albumService.searchAlbumPaged(album, page, size);
        Result<PageInfo<Album>> result = new Result<>(true, StatusCode.OK, "根据条件分页查询相册成功", brands);
        return result;
    }

}
