package dat;

import dat.entities.Package;
import dat.enums.DeliveryStatus;
import dat.enums.HibernateConfigState;
import dat.exceptions.JpaException;
import dat.persistence.PackageDAO;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello GLS");
        PackageDAO packageDAO = PackageDAO.getInstance(HibernateConfigState.NORMAL);

        Package aPackage = Package.builder().trackingNumber("123456789").sender("John Doe").receiver("Jane Doe").deliveryStatus(DeliveryStatus.PENDING).createdDateTime(null).build();
        System.out.println("Før: \n" + aPackage);
        aPackage = packageDAO.create(aPackage);
        System.out.println("Efter: \n" + aPackage);

        Package foundPackage = packageDAO.findById(1L);
        System.out.println("Found package: \n" + foundPackage);

        Package foundPackageByTrackingNumber = packageDAO.findByTrackingNumber("123456789");
        System.out.println("Found package by tracking number: \n" + foundPackageByTrackingNumber);
/*
        boolean deleted = packageDAO.delete(foundPackageByTrackingNumber);
        System.out.println("Deleted: " + deleted);
        deleted = packageDAO.delete(foundPackageByTrackingNumber);
        System.out.println("Deleted: " + deleted);
*/

        try {
            Package updatedPackage = Package.builder()
                .id(foundPackageByTrackingNumber.getId())
                .trackingNumber(foundPackageByTrackingNumber.getTrackingNumber())
                .sender("Benny Balle")
                .receiver(foundPackageByTrackingNumber.getReceiver())
                .createdDateTime(foundPackageByTrackingNumber.getCreatedDateTime())
                .deliveryStatus(DeliveryStatus.DELIVERED).build();

            foundPackageByTrackingNumber = packageDAO.update(updatedPackage);
            System.out.println("Updated: \n" + foundPackageByTrackingNumber);
        }
        catch (JpaException e) {
            System.out.println("Den gik sgu ikke granberg\n" + e.getMessage());
        }
    }

}