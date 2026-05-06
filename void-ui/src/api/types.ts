export type RegisterRequest = {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
};

export type AuthResponse = {
    token: string;
}