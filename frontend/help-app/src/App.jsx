import './App.css'

import React, { useState, useEffect } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Container,
  TextField,
  Card,
  CardContent,
  List,
  ListItem,
  Snackbar,
  CircularProgress,
  Box,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
} from "@mui/material";
import axios from "axios";

const App = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [isCreatingAccount, setIsCreatingAccount] = useState(false);
  const [token, setToken] = useState("");
  const [userRole, setUserRole] = useState(null);
  const [requests, setRequests] = useState([]);
  const [newRequestMessage, setNewRequestMessage] = useState("");
  const [feedback, setFeedback] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [userDetails, setUserDetails] = useState(null);

  const baseUrl = "http://localhost";

  const showMessage = (message) => {
    setSnackbarMessage(message);
    setSnackbarOpen(true);
  };

  const handleLogin = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        `${baseUrl}:8084/connect`,
        {},
        {
          params: { username, password },
        }
      );
      setToken(response.data.token);
      setUserRole(response.data.role); // Assuming the backend returns the role
      fetchUserDetails();
      showMessage("Logged in successfully!");
    } catch (error) {
      showMessage("Login failed!");
    } finally {
      setLoading(false);
    }
  };

  const handleAccountCreation = async () => {
    setLoading(true);
    try {
      await axios.post(
        `${baseUrl}:8084/create_user`,
        {},
        {
          params: {
            username,
            password,
            idRole: role,
          },
        }
      );
      showMessage("Account created successfully! You can now log in.");
      setIsCreatingAccount(false);
    } catch (error) {
      showMessage("Failed to create account.");
    } finally {
      setLoading(false);
    }
  };

  const fetchRequests = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${baseUrl}:8082/get_request`);
      setRequests(response.data);
    } catch (error) {
      showMessage("Failed to fetch requests.");
    } finally {
      setLoading(false);
    }
  };

  const fetchUserDetails = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${baseUrl}:8084/get_user`, {
        params: { id: 1 }, // Example ID, replace with logged-in user's ID
      });
      setUserDetails(response.data);
    } catch (error) {
      showMessage("Failed to fetch user details.");
    } finally {
      setLoading(false);
    }
  };

  const createRequest = async () => {
    setLoading(true);
    try {
      await axios.post(
        `${baseUrl}:8082/create_request`,
        {},
        {
          params: {
            idUser: 1, // Example User ID
            message: newRequestMessage,
            token,
          },
        }
      );
      showMessage("Request created successfully!");
      setNewRequestMessage("");
      fetchRequests();
    } catch (error) {
      showMessage("Failed to create request.");
    } finally {
      setLoading(false);
    }
  };

  const sendFeedback = async (idRequest) => {
    setLoading(true);
    try {
      await axios.post(
        `${baseUrl}:8081/create_feedback`,
        {},
        {
          params: {
            idUser: 2, // Example User ID
            idRequest,
            message: feedback,
            token,
          },
        }
      );
      showMessage("Feedback sent successfully!");
      setFeedback("");
    } catch (error) {
      showMessage("Failed to send feedback.");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    setToken("");
    setUserRole(null);
    setUsername("");
    setPassword("");
    setRequests([]);
    setUserDetails(null);
    setNewRequestMessage("");
    setFeedback("");
    showMessage("Logged out successfully!");
  };

  useEffect(() => {
    if (token) {
      fetchRequests();
    }
  }, [token]);

  return (
    <div>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">Help App</Typography>
          {token && (
            <Button color="inherit" onClick={handleLogout} style={{ marginLeft: "auto" }}>
              Logout
            </Button>
          )}
        </Toolbar>
      </AppBar>
      <Container style={{ marginTop: "20px" }}>
        {loading && (
          <Box display="flex" justifyContent="center" my={2}>
            <CircularProgress />
          </Box>
        )}
        {!token && !isCreatingAccount && (
          <div>
            <Typography variant="h5">Login</Typography>
            <TextField
              label="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              fullWidth
              style={{ marginBottom: "10px" }}
            />
            <TextField
              label="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              fullWidth
              style={{ marginBottom: "10px" }}
            />
            <Button variant="contained" onClick={handleLogin}>
              Login
            </Button>
            <Button
              variant="text"
              style={{ marginLeft: "10px" }}
              onClick={() => setIsCreatingAccount(true)}
            >
              Create Account
            </Button>
          </div>
        )}

        {isCreatingAccount && (
          <div>
            <Typography variant="h5">Create Account</Typography>
            <TextField
              label="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              fullWidth
              style={{ marginBottom: "10px" }}
            />
            <TextField
              label="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              fullWidth
              style={{ marginBottom: "10px" }}
            />
            <FormControl fullWidth style={{ marginBottom: "10px" }}>
              <InputLabel id="role-select-label">Role</InputLabel>
              <Select
                labelId="role-select-label"
                value={role}
                onChange={(e) => setRole(e.target.value)}
              >
                <MenuItem value={1}>User</MenuItem>
                <MenuItem value={2}>Volunteer</MenuItem>
                <MenuItem value={3}>Admin</MenuItem>
              </Select>
            </FormControl>
            <Button variant="contained" onClick={handleAccountCreation}>
              Create Account
            </Button>
            <Button
              variant="text"
              style={{ marginLeft: "10px" }}
              onClick={() => setIsCreatingAccount(false)}
            >
              Back to Login
            </Button>
          </div>
        )}

        {token && (
          <div>
            {userRole === 1 && (
              <div>
                <Typography variant="h5">Your Details</Typography>
                <Typography>Name: {userDetails?.username}</Typography>
                <Typography>Role: User</Typography>
                <Typography variant="h5" style={{ marginTop: "20px" }}>
                  Create a New Request
                </Typography>
                <TextField
                  label="Request Message"
                  value={newRequestMessage}
                  onChange={(e) => setNewRequestMessage(e.target.value)}
                  fullWidth
                  style={{ marginBottom: "10px" }}
                />
                <Button variant="contained" onClick={createRequest}>
                  Create Request
                </Button>
              </div>
            )}

            {userRole === 2 && (
              <div>
                <Typography variant="h5">Active Requests</Typography>
                <List>
                  {requests.map((req) => (
                    <ListItem key={req.id}>
                      <Card style={{ width: "100%" }}>
                        <CardContent>
                          <Typography>{req.message}</Typography>
                          <TextField
                            label="Feedback"
                            value={feedback}
                            onChange={(e) => setFeedback(e.target.value)}
                            fullWidth
                            style={{ marginTop: "10px" }}
                          />
                          <Button
                            variant="contained"
                            onClick={() => sendFeedback(req.id)}
                            style={{ marginTop: "10px" }}
                          >
                            Send Feedback
                          </Button>
                        </CardContent>
                      </Card>
                    </ListItem>
                  ))}
                </List>
              </div>
            )}
          </div>
        )}
      </Container>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        message={snackbarMessage}
        onClose={() => setSnackbarOpen(false)}
      />
    </div>
  );
};

export default App;
