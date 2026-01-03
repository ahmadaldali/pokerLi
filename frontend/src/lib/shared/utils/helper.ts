export const structuredObjectClone = (obj: any) => {
    return obj ? JSON.parse(JSON.stringify(obj)) : {};
};