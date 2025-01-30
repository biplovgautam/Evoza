
package com.evoza.profile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.evoza.utils.ProfileManager;
import com.evoza.utils.AuthenticationManager;

public class ProfileTest {
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_FULLNAME = "Test User";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "testpass123";

    @BeforeEach
    void setUp() {
        // Clean up test user if exists
        ProfileManager.deleteProfile(TEST_USERNAME);
    }

    @Test
    void testCreateProfile() {
        boolean result = ProfileManager.createProfile(
            TEST_USERNAME, 
            TEST_FULLNAME, 
            TEST_EMAIL, 
            TEST_PASSWORD, 
            1, // profilePicId
            false // isActive
        );
        assertTrue(result, "Profile creation should succeed");
    }

    @Test
    void testDuplicateUsername() {
        // Create first profile
        ProfileManager.createProfile(TEST_USERNAME, TEST_FULLNAME, TEST_EMAIL, TEST_PASSWORD, 1, false);
        
        // Try creating duplicate
        boolean result = ProfileManager.createProfile(
            TEST_USERNAME, 
            "Other Name", 
            "other@example.com", 
            "otherpass", 
            1, 
            false
        );
        assertFalse(result, "Duplicate username should fail");
    }

    @Test
    void testAuthentication() {
        // Create profile
        ProfileManager.createProfile(TEST_USERNAME, TEST_FULLNAME, TEST_EMAIL, TEST_PASSWORD, 1, true);
        
        // Test authentication
        boolean auth = AuthenticationManager.authenticate(TEST_USERNAME, TEST_PASSWORD);
        assertTrue(auth, "Authentication should succeed with correct credentials");
        
        // Test wrong password
        auth = AuthenticationManager.authenticate(TEST_USERNAME, "wrongpass");
        assertFalse(auth, "Authentication should fail with wrong password");
    }

    @Test
    void testEmailVerification() {
        // Create profile
        ProfileManager.createProfile(TEST_USERNAME, TEST_FULLNAME, TEST_EMAIL, TEST_PASSWORD, 1, false);
        
        // Get profile ID
        Integer profileId = ProfileManager.getProfileIdByUsername(TEST_USERNAME);
        assertNotNull(profileId, "Profile ID should not be null");
        
        // Generate OTP
        String otp = ProfileManager.generateAndStoreOTP(profileId);
        assertNotNull(otp, "OTP should be generated");
        
        // Verify OTP
        boolean verified = ProfileManager.verifyOTP(profileId, otp);
        assertTrue(verified, "OTP verification should succeed");
        
        // Check if user is active
        boolean isActive = ProfileManager.isUserActive(TEST_USERNAME);
        assertTrue(isActive, "User should be active after OTP verification");
    }
}
