import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * The IOHandler class provides some simple methods for file input or output.
 * Title : IOHandler.java
 * Description:
 * The IOHandler class provides some simple methods for file input or output.
 * The most important feature of this class is that it can switch input and
 * output flexibly
 * and it has complete exception handling.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class IOHandler
{
    private String filename;
    private File file;

    /**
     * Constructor of IOHandler.
     * @param filename the name of the file which needs to be read/write.
     */
    public IOHandler(String filename)
    {
        this.filename = filename;
        file = new File(filename);
    }

    private boolean CheckExist() { return file.exists(); }

    /**
     * write data to the file.
     * @param data the data needed to be write
     * @return Whether it was success or not
     */
    public boolean WriteFile(String data)
    {
        OutputStream os;
        try
        {
            os = new FileOutputStream(filename);
            PrintWriter pw = new PrintWriter(os);
            pw.print(data);
            pw.close();
            os.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * read data from the file.
     * @return the data which are read from the file
     */
    public String ReadFile()
    {
        if (!CheckExist()) { return null; }
        int iAvail;
        String res = null;
        try
        {
            InputStream is = new FileInputStream(filename);
            iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            is.close();
            res = new String(bytes, "UTF-8");
        }
        catch (IOException e) { e.printStackTrace(); }
        return res;
    }

    /**
     * get the file name.
     * @return the filename
     */
    public String getFilename() { return filename; }

    /**
     * set the file name.
     * @param filename the filename to set
     */
    public void setFilename(String filename) { this.filename = filename; }

    /**
     * get the file object.
     * @return the file
     */
    public File getFile() { return file; }

    /**
     * set the file object
     * @param file the file to set
     */
    public void setFile(File file) { this.file = file; }

}
