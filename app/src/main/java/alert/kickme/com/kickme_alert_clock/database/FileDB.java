package alert.kickme.com.kickme_alert_clock.database;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import alert.kickme.com.kickme_alert_clock.global.AppContext;


public class FileDB {

    private final Context context;
    private List<Long> longList;
    private Long[] longs;
    private byte[] data;

    public FileDB(Context context) {
        this.context = context;
    }

    public File getFile(int id, @NonNull Type type) {
        return new File(getPath(id, type));
    }

    public String getPath(int id, @NonNull Type type) {
        return context.getFilesDir() + "/" + type.name() + id;
    }

    public void saveArrayLong(int id, @NonNull Type type, Long[] longs) {
        this.longs = longs;
        write(getFile(id,type),new HandleFile(){
            @Override
            void onWrite(OutputStream outputStream) throws IOException {
                byte[] bytes = new byte[8];
                for (Long aLong : FileDB.this.longs) {
                    if (aLong == null) aLong = -1L;
                    ByteBuffer.wrap(bytes).putLong(aLong);
                    outputStream.write(bytes);
                }
            }
        });
    }

    public List<Long> getArrayLong(int id, @NonNull Type type){
        return getArrayLong(getFile(id, type));
    }

    public List<Long> getArrayLong(File file) {
        longList = new ArrayList<>();
        write(file,new HandleFile(){
            @Override
            void onRead(InputStream inputStream) throws IOException {
                byte[] bytes = new byte[8];
                while (inputStream.read(bytes) != -1) {
                    Long aLong = ByteBuffer.wrap(bytes).getLong();
                    if (aLong == -1) aLong = null;
                    longList.add(aLong);
                }
            }
        });
        return longList;
    }

    public void  saveFile(String path, byte[] data){
        this.data = data;
        write(new File(path),new HandleFile(){
            @Override
            void onWrite(OutputStream outputStream) throws IOException {
                outputStream.write(FileDB.this.data);
            }
        });
    }

    private void write(File file, HandleFile handleFile){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            if (handleFile != null){
                handleFile.onWrite(outputStream);
            }
        }catch (Exception e) {
            AppContext.CatchException(context,e,"");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    AppContext.CatchException(context,e,"");
                }
            }
        }
    }

    private void read(File file,HandleFile handleFile){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            if (handleFile != null){
                handleFile.onRead(inputStream);
            }
        } catch (Exception e) {
            AppContext.CatchException(context,e,"");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                   AppContext.CatchException(context,e,"");
                }
            }
        }
    }

    private static class HandleFile {
        void onWrite(OutputStream outputStream)  throws IOException{

        }
        void onRead(InputStream inputStream) throws IOException {}
    }

    public enum Type {
        ARRAY_DATE_MILLS, ARRAY_DAY_MILLS
    }

}
