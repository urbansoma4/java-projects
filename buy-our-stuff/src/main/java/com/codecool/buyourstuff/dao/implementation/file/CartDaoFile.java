package com.codecool.buyourstuff.dao.implementation.file;

import com.codecool.buyourstuff.dao.CartDao;
import com.codecool.buyourstuff.model.Cart;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartDaoFile implements CartDao {
    private static final File CART_FILE = new File("data/cart.csv");
    private static final String DATA_SEPARATOR = ";";
    private static int highestId;

    public CartDaoFile() {
        if (CART_FILE.exists()) {
            highestId = findHighestId();
        }
    }

    private static int findHighestId() {
        int result;
        try (Scanner scanner = new Scanner(CART_FILE)) {
            scanner.useDelimiter(DATA_SEPARATOR);
            int lastUserId = 0;

            while (scanner.hasNextLine()) {
                lastUserId = scanner.nextInt();
                scanner.nextLine();
            }
            result = lastUserId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void add(Cart cart) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE, true))) {
            int id = cart.getId();
            if (id <= highestId) {
                id = ++highestId;
                cart.setId(id);
            } else {
                highestId = id;
            }
            String line = id + DATA_SEPARATOR + cart.getCurrency();
            writer.append(line).append(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cart find(int id) {
        Cart cart = null;
        String line;
        int lineCounter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            while ((line = reader.readLine()) != null && cart == null) {
                lineCounter++;
                String[] values = line.split(DATA_SEPARATOR);
                int resultId = Integer.parseInt(values[0]);
                String resultCurrency = values[1];
                if (resultId == id) {
                    cart = new Cart(resultCurrency);
                    cart.setId(resultId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID in file at line: " + lineCounter);
        }
        if (cart == null) {
            throw new DataNotFoundException("Cart with id = " + id + " not found!");
        }
        return cart;
    }

    @Override
    public void remove(int id) {
        List<Cart> carts = getAll();
        carts.removeIf(c -> c.getId() == id);
        overWriteListOfCartsInFile(carts);
    }

    @Override
    public void clear() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE, false))) {
            writer.append("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> carts = new ArrayList<>();
        String line;
        int lineCounter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            while ((line = reader.readLine()) != null) {
                lineCounter++;
                String[] values = line.split(DATA_SEPARATOR);
                int resultId = Integer.parseInt(values[0]);
                String resultCurrency = values[1];
                Cart cart = new Cart(resultCurrency);
                cart.setId(resultId);
                carts.add(cart);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (
                NumberFormatException e) {
            System.out.println("Invalid ID in file at line: " + lineCounter);
        }
        return carts;
    }

    private void overWriteListOfCartsInFile(List<Cart> carts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE, false))) {
            for (Cart cart : carts) {
                String line = cart.getId() + DATA_SEPARATOR + cart.getCurrency();
                writer.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
