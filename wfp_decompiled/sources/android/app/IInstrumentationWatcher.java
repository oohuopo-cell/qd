package android.app;

import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: /workspace/wfp_patch/classes.dex */
public interface IInstrumentationWatcher extends IInterface {

    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static class Default implements IInstrumentationWatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IInstrumentationWatcher
        public void instrumentationFinished(ComponentName componentName, int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IInstrumentationWatcher
        public void instrumentationStatus(ComponentName componentName, int i, Bundle bundle) throws RemoteException {
        }
    }

    /* loaded from: /workspace/wfp_patch/classes.dex */
    public static abstract class Stub extends Binder implements IInstrumentationWatcher {
        public static final String DESCRIPTOR = "android.app.IInstrumentationWatcher";
        static final int TRANSACTION_instrumentationFinished = 2;
        static final int TRANSACTION_instrumentationStatus = 1;

        /* loaded from: /workspace/wfp_patch/classes.dex */
        private static class Proxy implements IInstrumentationWatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationFinished(ComponentName componentName, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(Stub.TRANSACTION_instrumentationFinished, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IInstrumentationWatcher
            public void instrumentationStatus(ComponentName componentName, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(Stub.TRANSACTION_instrumentationStatus, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInstrumentationWatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IInstrumentationWatcher)) ? new Proxy(iBinder) : (IInstrumentationWatcher) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case TRANSACTION_instrumentationStatus /* 1 */:
                    return "instrumentationStatus";
                case TRANSACTION_instrumentationFinished /* 2 */:
                    return "instrumentationFinished";
                default:
                    return null;
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return TRANSACTION_instrumentationStatus;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= TRANSACTION_instrumentationStatus && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            switch (i) {
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (i) {
                        case TRANSACTION_instrumentationStatus /* 1 */:
                            ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                            int readInt = parcel.readInt();
                            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                            parcel.enforceNoDataAvail();
                            instrumentationStatus(componentName, readInt, bundle);
                            parcel2.writeNoException();
                            return true;
                        case TRANSACTION_instrumentationFinished /* 2 */:
                            ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                            int readInt2 = parcel.readInt();
                            Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                            parcel.enforceNoDataAvail();
                            instrumentationFinished(componentName2, readInt2, bundle2);
                            parcel2.writeNoException();
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }
    }

    void instrumentationFinished(ComponentName componentName, int i, Bundle bundle) throws RemoteException;

    void instrumentationStatus(ComponentName componentName, int i, Bundle bundle) throws RemoteException;
}
