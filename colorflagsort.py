# Sort into Red, White, Blue

sample_arr = ["R", "R", "W", "B", "W", "B", "R", "B", "W", "B", "R", "W", "B"]
sample_arr2 = ["W", "W", "B", "B", "R", "R", "B", "W", "W", "R", "B", "W"]

def swap(arr, i, j):
    temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp

def color(arr, i):
    if arr[i] == "R":
        return "Red"
    elif arr[i] == "W":
        return "White"
    elif arr[i] == "B":
        return "Blue"
    else:
        return "Error"

def main(arr):
    string = ""
    string2 = ""

    for c in arr:
        string = string + c + " "
    print(string)

    red_pointer = 0
    blue_pointer = len(arr) - 1

    while (color(arr, red_pointer) == "Red"):
        red_pointer = red_pointer + 1

    while (color(arr, blue_pointer) == "Blue"):
        blue_pointer = blue_pointer - 1

    swap_count = 0
    color_count = 0

    start = red_pointer

    while (start < blue_pointer):
        curr_color = color(arr, start)
        color_count = color_count + 1

        if (curr_color == "Red"):
            swap(arr, start, red_pointer)
            swap_count = swap_count + 1
            red_pointer = red_pointer + 1
            start = start + 1
        elif (curr_color == "Blue"):
            swap(arr, start, blue_pointer)
            swap_count = swap_count + 1
            blue_pointer = blue_pointer - 1
        elif (curr_color == "White"):
            start = start + 1

    print("Swap(): " + str(swap_count))
    print("Color(): " + str(color_count))
    print("Size: " + str(len(arr)))


    for c in arr:
        string2 = string2 + c + " "
    print(string2)
    
main(sample_arr)
print("_________________________________________________")
main(sample_arr2)