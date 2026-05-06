import {
  TextInput,
  PasswordInput,
  Button,
  Paper,
  Text,
  Stack,
  Anchor,
  Container,
  useMantineColorScheme
} from "@mantine/core";
import { useForm } from "@mantine/form";
import { useNavigate } from "react-router-dom";
import { signUp } from "../api/api";
import type { AuthResponse } from "../api/types";

export default function SignupPage() {
  const navigate = useNavigate();
  const { colorScheme } = useMantineColorScheme();
  const dark = colorScheme === "dark";

  const form = useForm({
    initialValues: {
      firstName: "",
      lastName: "",
      username: "",
      email: "",
      password: "",
    },
    validate: {
      firstName: (val) => (val.length === 0 ? "First name is required" : null),
      lastName: (val) => (val.length === 0 ? "Last name is required" : null),
      username: (val) => (val.length === 0 ? "Username is required" : null),
      email: (val) => (/^\S+@\S+$/.test(val) ? null : "Invalid email"),
      password: (val) =>
        val.length < 6 ? "Password must be at least 6 characters" : null,
    },
  });

  const handleSignup = async (values: typeof form.values) => {
    try {
      const response: AuthResponse = await signUp(values);
      // store token and navigate to home
      // localStorage.setItem("token", response.token);
      navigate("/login");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Container size={460} py={80}>
      <Text
        style={{
          fontFamily: "Georgia, serif",
          fontSize: "28px",
          color: dark ? "#f5f4f0" : "#0a0a0a",
          marginBottom: "8px",
        }}
      >
        Join the void.
      </Text>
      <Text
        style={{
          fontFamily: "Courier New, monospace",
          fontSize: "12px",
          color: "#888888",
          marginBottom: "32px",
        }}
      >
        Already have an account?{" "}
        <Anchor
          onClick={() => navigate("/login")}
          style={{ color: dark ? "#f5f4f0" : "#0a0a0a", fontSize: "12px" }}
        >
          Login
        </Anchor>
      </Text>

      <Paper
        radius="sm"
        p="xl"
        withBorder
        style={{
          backgroundColor: dark ? "#111111" : "#eceae4",
          borderColor: dark ? "#222222" : "#dddcda",
        }}
      >
        <form onSubmit={form.onSubmit(handleSignup)}>
          <Stack gap="sm">
            <TextInput
              label="First Name"
              placeholder="Enter first name ..."
              {...form.getInputProps("firstName")}
            />
            <TextInput
              label="Last Name"
              placeholder="Enter last name ..."
              {...form.getInputProps("lastName")}
            />
            <TextInput
              label="Username"
              placeholder="Enter username ..."
              {...form.getInputProps("username")}
            />
            <TextInput
              label="Email"
              placeholder="Enter email ..."
              {...form.getInputProps("email")}
            />
            <PasswordInput
              label="Password"
              placeholder="At least 6 characters"
              {...form.getInputProps("password")}
            />
            <Button
              type="submit"
              fullWidth
              mt="md"
              color="dark"
              style={{
                fontFamily: "Courier New, monospace",
                letterSpacing: "2px",
                fontSize: "11px",
              }}
            >
              CREATE ACCOUNT
            </Button>
          </Stack>
        </form>
      </Paper>
    </Container>
  );
}