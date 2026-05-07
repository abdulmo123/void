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
import type { AuthResponse } from "../api/types";
import { login } from "../api/api";

export default function LoginPage() {
    const navigate = useNavigate();
    const { colorScheme } = useMantineColorScheme();
    const dark = colorScheme === 'dark';

    const form = useForm({
        initialValues: {
            username: "",
            password: "",
        },
        // validate: {

        // },
    });

    const handleLogin = async (values: typeof form.values) => {
        try {
            const response: AuthResponse = await login(values);
            localStorage.setItem("token", response.token);
            navigate('/home');
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
                Enter into the void.
            </Text>
            <Text
                style={{
                    fontFamily: "Courier New, monospace",
                    fontSize: "12px",
                    color: "#888888",
                    marginBottom: "32px",
                }}
            >
                New to the void?{" "}
                <Anchor
                    onClick={() => navigate("/signup")}
                    style={{ color: dark ? "#f5f4f0" : "#0a0a0a", fontSize: "12px" }}
                >
                    SIGN UP
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
                <form onSubmit={form.onSubmit(handleLogin)}>
                    <Stack gap="sm">
                        <TextInput
                            label="Username"
                            placeholder="Enter username or email ..."
                            {...form.getInputProps("username")}
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
                            SIGN IN
                        </Button>
                    </Stack>
                </form>
            </Paper>
        </Container>
    );
}