package com.dinchan.service.impl;

import com.dinchan.config.JwtProvider;
import com.dinchan.domain.AccountStatus;
import com.dinchan.domain.USER_ROLE;
import com.dinchan.exceptions.SellerException;
import com.dinchan.model.Address;
import com.dinchan.model.Seller;
import com.dinchan.repository.AddressRepository;
import com.dinchan.repository.SellerRepository;
import com.dinchan.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller existedSeller = sellerRepository.findByEmail(seller.getEmail());
        if (existedSeller != null) {
            throw new Exception("seller already exist, used different email");
        }
        Address savedAddress = addressRepository.save(seller.getPickupAddress());
        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setPhoneNumber(seller.getPhoneNumber());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerException("Seller not found with id: "+ id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new SellerException("Seller not found...");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findAll();
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existedSeller = getSellerById(id);
        if (seller.getSellerName() != null) {
            existedSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getPhoneNumber() != null) {
            existedSeller.setPhoneNumber(seller.getPhoneNumber());
        }
        if (seller.getEmail() != null) {
            existedSeller.setEmail(seller.getEmail());
        }
        if (seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null) {
            existedSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if (seller.getPickupAddress() != null && seller.getPickupAddress().getAddress() != null
            && seller.getPickupAddress().getPhoneNumber() != null
            && seller.getPickupAddress().getCity() != null
            && seller.getPickupAddress().getState() != null) {
                existedSeller.getPickupAddress()
                        .setAddress(seller.getPickupAddress().getAddress());
                existedSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
                existedSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
                existedSeller.getPickupAddress()
                        .setPhoneNumber(seller.getPickupAddress().getPhoneNumber());
                existedSeller.getPickupAddress().setZipCode(seller.getPickupAddress().getZipCode());
        }
        return sellerRepository.save(existedSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller existedSeller = getSellerById(id);
        sellerRepository.delete(existedSeller);
    }

}
