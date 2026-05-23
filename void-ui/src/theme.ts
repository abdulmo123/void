import { createTheme, type MantineColorsTuple } from '@mantine/core';

const warmWhite: MantineColorsTuple = [
  '#f5f4f0',
  '#e8e6e0',
  '#d5d2c8',
  '#c2beb0',
  '#b0ab98',
  '#9e9880',
  '#8c8568',
  '#7a7250',
  '#685f38',
  '#564c20',
];

export const theme = createTheme({
  fontFamily: 'Courier New, monospace',
  fontFamilyMonospace: 'Courier New, monospace',
  headings: {
    fontFamily: 'Georgia, serif',
  },
  primaryColor: 'dark',
  colors: {
    warmWhite,
  },
  defaultRadius: 'sm',
  black: '#0a0a0a',
  white: '#f5f4f0',
  other: {
    // light mode
    lightBg: '#f5f4f0',
    lightCard: '#eceae4',
    lightBorder: '#dddcda',
    lightText: '#0a0a0a',
    lightMuted: '#888888',

    // dark mode
    darkBg: '#0a0a0a',
    darkCard: '#111111',
    darkBorder: '#222222',
    darkText: '#f5f4f0',
    darkMuted: '#888888',
  }
});