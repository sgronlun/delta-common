package delta.common.utils.sharedMemory;

public class MainTestSHM
{
  public static void dumpSHM(int key_p)
  {
    SharedMemorySegment shm_l=SHMFactory.getInstance().lookup(key_p);
    if (shm_l==null)
    {
      System.err.println("Segment does not exists !");
    }
    else
    {
      boolean attachSuccessfull_l=shm_l.attach(true);
      if (attachSuccessfull_l)
      {
        int size_l=shm_l.getSize();
        byte[] data_l=new byte[size_l];
        shm_l.readBuffer(data_l,0,0,data_l.length);
        for(int i=0;i<data_l.length;i++)
        {
          System.out.print(data_l[i]+" ");
        }
        System.out.println("");
        boolean returnDetach_l=shm_l.detach();
        if (!returnDetach_l)
        {
          System.err.println("Detach failed !");
        }
      }
      else
      {
        System.err.println("Could not attach to segment !");
      }
    }
  }

  public static int execute()
  {
    int return_l=1;
    int key_l=0x00020001;
    boolean created_l=false;
    SharedMemorySegment shm_l=SHMFactory.getInstance().lookup(key_l);
    if (shm_l==null)
    {
      System.out.println("Segment does not exists. Creating it !");
      shm_l=SHMFactory.getInstance().create(key_l,1000);
      if (shm_l!=null)
      {
        created_l=true;
      }
    }
    if (shm_l!=null)
    {
      System.out.println("Found or created usable segment. Using it !");
      boolean attachSuccessfull_l=shm_l.attach(false);
      if (attachSuccessfull_l)
      {
        try
        {
          // Byte test
          {
            byte valueToWrite_l=5;
            shm_l.writeByte(valueToWrite_l,0);
            byte value_l=shm_l.readByte(0);
            if (value_l!=valueToWrite_l)
            {
              System.err.println("Written value : "+valueToWrite_l+", read : "+value_l);
            }
            else
            {
              System.out.println("Write+read cycle OK !");
            }
          }
          // Int test
          {
            int valueToWrite_l=66666666;
            shm_l.writeInt(valueToWrite_l,0);
            int value_l=shm_l.readInt(0);
            if (value_l!=valueToWrite_l)
            {
              System.err.println("Written value : "+valueToWrite_l+", read : "+value_l);
            }
            else
            {
              System.out.println("Write+read cycle OK !");
            }
          }
        }
        catch (SHMException shmException)
        {
          shmException.printStackTrace();
        }
      }
      else
      {
        System.err.println("Could not attach to segment !");
      }
      boolean returnDetach_l=shm_l.detach();
      if (!returnDetach_l)
      {
        System.err.println("Detach failed !");
      }
      if (created_l)
      {
        int returnDestroy_l=SHMFactory.getInstance().destroy(shm_l);
        if (returnDestroy_l!=SHMErrorCodes.OK)
        {
          System.err.println("Error while destroying memory segment !");
        }
      }
    }
    return return_l;
  }

  public static void main(String[] args)
  {
    // int key_l=0x00020001;
    // TestSHM.dumpSHM(key_l);
    MainTestSHM.execute();
  }
}
