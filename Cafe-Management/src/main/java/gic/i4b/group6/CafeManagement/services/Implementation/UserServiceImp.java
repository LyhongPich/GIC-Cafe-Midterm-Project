package gic.i4b.group6.CafeManagement.services.Implementation;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Invoices;
import gic.i4b.group6.CafeManagement.models.Roles;
import gic.i4b.group6.CafeManagement.models.Users;
import gic.i4b.group6.CafeManagement.repositories.InvoiceRepository;
import gic.i4b.group6.CafeManagement.repositories.RoleRepository;
import gic.i4b.group6.CafeManagement.repositories.UserRepository;
import gic.i4b.group6.CafeManagement.services.UserService;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private InvoiceRepository invoiceRepository;

    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, InvoiceRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.invoiceRepository = orderRepository;
    }

    @Override
    public void setUser(String firstName, String lastName, String gender, String dob, String username, String password,
            MultipartFile profile) {

        int state = 0;

        List<Users> users = getAllUsers();
        for(Users user : users) {
            if(username.equals(user.getUsername())) {
                state = 1;
                break;
            }
        }
        if(state == 0) {
            Users addUser = new Users();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar now = Calendar.getInstance();

            Calendar input = Calendar.getInstance();
            try {
                input.setTime(format.parse(dob));
            } catch (ParseException e) {

            }

            if(now.compareTo(input) > 0) {

                String file = profile.getOriginalFilename();
                String cleanFile = new File(file).getName();
                if(cleanFile.contains("..")) {
                    System.out.println("not a valid file");
                }
                try{
                    addUser.setProfile(Base64.getEncoder().encodeToString(profile.getBytes()));
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                addUser.setFirst_name(firstName);
                addUser.setLast_name(lastName);
                addUser.setGender(gender);
                addUser.setOrder_serve(0);

                int stateRole = 0;
                int roleId = 0;

                try {
                    addUser.setDate_of_birth(format.parse(dob));
                    addUser.setHired_date(new Date());
                } catch (ParseException e) {

                }
                addUser.setUsername(username);
                addUser.setPassword(password);

                Calendar currentDate = Calendar.getInstance();
                Calendar birthDate = Calendar.getInstance();

                birthDate.setTime(addUser.getDate_of_birth());
                int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

                if(currentDate.get(Calendar.MONTH) <= birthDate.get(Calendar.MONTH) && currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
                addUser.setAge(age);

                List<Roles> roles = roleRepository.findAll();
                for(Roles role : roles) {
                    if(role.getRole().equals("Cashier")) {
                        stateRole = 1;
                        roleId = role.getId();
                        break;
                    }
                }
                if(stateRole == 1) {
                    Roles roleInp = roleRepository.findById(roleId).get();
                    addUser.setRoles(roleInp);
                }
                userRepository.save(addUser);
            }
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Users> getAllCashier() {
        List<Users> users = userRepository.findAll();
        List<Users> cashiers = new ArrayList<Users>();
        for(Users u : users) {
            Users use = new Users();
            if(u.getRoles().getId() == 2) {
                use.setId(u.getId());
                use.setFirst_name(u.getFirst_name());
                use.setLast_name(u.getLast_name());
                use.setGender(u.getGender());
                use.setDate_of_birth(u.getDate_of_birth());
                use.setHired_date(u.getHired_date());
                use.setUsername(u.getUsername());
                use.setPassword(u.getPassword());
                use.setProfile(u.getProfile());
                use.setLogin_date(u.getLogin_date());
                use.setOrder_serve(u.getOrder_serve());
                use.setAge(u.getAge());
                cashiers.add(use);
            }
        }
        return cashiers;
    }

    @Override
    public List<Integer> getAgeAllCashier() {
        List<Integer> ageList = new ArrayList<Integer>();
        List<Users> usersList = userRepository.findAll();
        Calendar currentDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        for(Users u : usersList) {
            if(u.getRoles().getId() == 2) {
                birthDate.setTime(u.getDate_of_birth());
                int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

                if(currentDate.get(Calendar.MONTH) <= birthDate.get(Calendar.MONTH) && currentDate.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
                ageList.add(age);
            }
        }
        return ageList;
    }

    @Override
    public Users getCashierById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void editUser(Integer id, String firstName, String lastName, String gender, String dob, String username, String password,
            MultipartFile profile) {

        Users editUser = getCashierById(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar now = Calendar.getInstance();

        Calendar input = Calendar.getInstance();
        try {
            input.setTime(format.parse(dob));
        } catch (ParseException e) {

        }

        if(now.compareTo(input) > 0) {

            String file = profile.getOriginalFilename();
            String cleanFile = new File(file).getName();
            if(cleanFile.contains("..")) {
                System.out.println("not a valid file");
            }
            try{
                editUser.setProfile(Base64.getEncoder().encodeToString(profile.getBytes()));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            editUser.setFirst_name(firstName);
            editUser.setLast_name(lastName);
            editUser.setGender(gender);

            try {
                editUser.setDate_of_birth(format.parse(dob));
            } catch (ParseException e) {

            }
            editUser.setUsername(username);
            editUser.setPassword(password);
            
            userRepository.save(editUser);
        }
    }

    @Override
    public void removeUser(Integer id) {
        Users user = userRepository.findById(id).orElse(null);
        if(user != null && user.getRoles() != null) {
            user.getRoles().setUsers(null);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Users getAdmin() {
        List<Users> users = getAllUsers();
        for(Users user : users) {
            if(user.hasRole("Admin")) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void setOrderServe(Integer cashierId, Integer tableId) {
        // Tables table = tableRepository.findById(tableId).get();
        Users user = userRepository.findById(cashierId).get();
        List<Invoices> invoiceList = invoiceRepository.findByUsers(user);

        if(user != null) {
            user.setOrder_serve(invoiceList.size());
        }
        userRepository.save(user);
    }
}
