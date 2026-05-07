import { useState } from "react";
import {
    Container,
    Text,
    Textarea,
    Button,
    Stack,
    Group,
    Avatar,
    Paper,
    Divider,
    ActionIcon,
    useMantineColorScheme,
} from "@mantine/core";
import { IconHeart, IconMessageCircle, IconShare } from "@tabler/icons-react";

// Mock posts for now — replace with real API call later
const MOCK_POSTS = [
    {
        id: 1,
        username: "johndoe",
        createdAt: "2 hours ago",
        title: "Thoughts on microservices",
        content:
            "After building my first microservices project I can confidently say the hardest part isn't the code — it's resisting the urge to over-engineer from day one. Start with two services. That's it.",
    },
    {
        id: 2,
        username: "janedoe",
        createdAt: "5 hours ago",
        title: "On writing every day",
        content:
            "The blank page is not your enemy. The blank page is just waiting. It has infinite patience. You're the one who doesn't.",
    },
    {
        id: 3,
        username: "alex",
        createdAt: "1 day ago",
        title: "Why I deleted all my social media",
        content:
            "Six months ago I deleted everything. Twitter, Instagram, LinkedIn. The first week was anxiety. The second week was boredom. By the third week I had written more than I had in the previous year.",
    },
];

function PostCard({ post, dark }: { post: (typeof MOCK_POSTS)[0]; dark: boolean }) {
    const [liked, setLiked] = useState(false);

    return (
        <Paper
            radius="sm"
            p="xl"
            style={{
                backgroundColor: dark ? "#111111" : "#ffffff",
                borderColor: dark ? "#1e1e1e" : "#e8e6e0",
                border: `1px solid ${dark ? "#1e1e1e" : "#e8e6e0"}`,
                transition: "border-color 0.2s ease",
            }}
        >
            {/* Author row */}
            <Group mb="md" gap="sm">
                <Avatar
                    radius="xl"
                    size="sm"
                    style={{
                        backgroundColor: dark ? "#222222" : "#dddcda",
                        color: dark ? "#888888" : "#888888",
                        fontFamily: "Courier New, monospace",
                        fontSize: "11px",
                    }}
                >
                    {post.username[0].toUpperCase()}
                </Avatar>
                <div>
                    <Text
                        style={{
                            fontFamily: "Courier New, monospace",
                            fontSize: "12px",
                            color: dark ? "#dddddd" : "#0a0a0a",
                            fontWeight: 500,
                        }}
                    >
                        {post.username}
                    </Text>
                    <Text
                        style={{
                            fontFamily: "Courier New, monospace",
                            fontSize: "10px",
                            color: "#888888",
                            letterSpacing: "0.5px",
                        }}
                    >
                        {post.createdAt}
                    </Text>
                </div>
            </Group>

            {/* Title */}
            <Text
                style={{
                    fontFamily: "Georgia, serif",
                    fontSize: "18px",
                    color: dark ? "#f5f4f0" : "#0a0a0a",
                    marginBottom: "10px",
                    lineHeight: 1.3,
                }}
            >
                {post.title}
            </Text>

            {/* Content */}
            <Text
                style={{
                    fontFamily: "Courier New, monospace",
                    fontSize: "13px",
                    color: dark ? "#888888" : "#555555",
                    lineHeight: 1.8,
                    marginBottom: "20px",
                }}
            >
                {post.content}
            </Text>

            <Divider color={dark ? "#1e1e1e" : "#e8e6e0"} mb="md" />

            {/* Actions */}
            <Group gap="md">
                <ActionIcon
                    variant="subtle"
                    color={liked ? "red" : "gray"}
                    onClick={() => setLiked(!liked)}
                    size="sm"
                >
                    <IconHeart size={15} fill={liked ? "currentColor" : "none"} />
                </ActionIcon>
                <ActionIcon variant="subtle" color="gray" size="sm">
                    <IconMessageCircle size={15} />
                </ActionIcon>
                <ActionIcon variant="subtle" color="gray" size="sm">
                    <IconShare size={15} />
                </ActionIcon>
            </Group>
        </Paper>
    );
}

export default function HomePage() {
    const { colorScheme } = useMantineColorScheme();
    const dark = colorScheme === "dark";
    const [postContent, setPostContent] = useState("");
    const [postTitle, setPostTitle] = useState("");

    const handlePost = () => {
        if (!postContent.trim()) return;
        console.log("Post submitted:", { postTitle, postContent });
        setPostTitle("");
        setPostContent("");
    };

    return (
        <div
            style={{
                backgroundColor: dark ? "#0a0a0a" : "#f5f4f0",
                minHeight: "100vh",
                paddingTop: "40px",
                paddingBottom: "80px",
            }}
        >
            <Container size="sm">
                <Stack gap="lg">

                    {/* Page heading */}
                    <div>
                        <Text
                            style={{
                                fontFamily: "Courier New, monospace",
                                fontSize: "10px",
                                letterSpacing: "3px",
                                textTransform: "uppercase",
                                color: "#888888",
                                marginBottom: "6px",
                            }}
                        >
                            The Feed
                        </Text>
                        <Text
                            style={{
                                fontFamily: "Georgia, serif",
                                fontSize: "28px",
                                color: dark ? "#f5f4f0" : "#0a0a0a",
                            }}
                        >
                            What's in the void today<span style={{ color: "#888888" }}>.</span>
                        </Text>
                    </div>

                    {/* Composer */}
                    <Paper
                        radius="sm"
                        p="lg"
                        style={{
                            backgroundColor: dark ? "#111111" : "#ffffff",
                            border: `1px solid ${dark ? "#1e1e1e" : "#e8e6e0"}`,
                        }}
                    >
                        <input
                            placeholder="Title (optional)"
                            value={postTitle}
                            onChange={(e) => setPostTitle(e.target.value)}
                            style={{
                                width: "100%",
                                background: "none",
                                border: "none",
                                outline: "none",
                                fontFamily: "Georgia, serif",
                                fontSize: "16px",
                                color: dark ? "#f5f4f0" : "#0a0a0a",
                                marginBottom: "10px",
                            }}
                        />
                        <Divider color={dark ? "#1e1e1e" : "#e8e6e0"} mb="sm" />
                        <Textarea
                            placeholder="Put something into the void..."
                            value={postContent}
                            onChange={(e) => setPostContent(e.currentTarget.value)}
                            minRows={3}
                            autosize
                            variant="unstyled"
                            styles={{
                                input: {
                                    fontFamily: "Courier New, monospace",
                                    fontSize: "13px",
                                    color: dark ? "#888888" : "#555555",
                                    lineHeight: 1.8,
                                    padding: 0,
                                },
                            }}
                        />
                        <Group justify="flex-end" mt="md">
                            <Button
                                onClick={handlePost}
                                disabled={!postContent.trim()}
                                color="dark"
                                size="xs"
                                style={{
                                    fontFamily: "Courier New, monospace",
                                    letterSpacing: "2px",
                                    fontSize: "10px",
                                }}
                            >
                                POST
                            </Button>
                        </Group>
                    </Paper>

                    {/* Divider */}
                    <Divider
                        color={dark ? "#1e1e1e" : "#e8e6e0"}
                        label={
                            <Text style={{ fontFamily: "Courier New, monospace", fontSize: "10px", color: "#888888", letterSpacing: "2px" }}>
                                RECENT POSTS
                            </Text>
                        }
                        labelPosition="left"
                    />

                    {/* Post feed */}
                    <Stack gap="md">
                        {MOCK_POSTS.map((post) => (
                            <PostCard key={post.id} post={post} dark={dark} />
                        ))}
                    </Stack>

                </Stack>
            </Container>
        </div>
    );
}